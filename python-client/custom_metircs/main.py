"""
Submit metrics returns "Payload accepted" response
"""

import configparser
from datetime import datetime
import random
from datadog_api_client import ApiClient, Configuration
from datadog_api_client.v2.api.metrics_api import MetricsApi
from datadog_api_client.v2.model.metric_intake_type import MetricIntakeType
from datadog_api_client.v2.model.metric_payload import MetricPayload
from datadog_api_client.v2.model.metric_point import MetricPoint
from datadog_api_client.v2.model.metric_resource import MetricResource
from datadog_api_client.v2.model.metric_series import MetricSeries

# Create a ConfigParser object
config = configparser.ConfigParser()

# Read the config.ini file
config.read('config.ini')

devices = ['windows', 'mac', 'TV', 'smartphone', 'tablet', 'router', 'printer', 'game console', 'smartwatch', 'others']
hosts = ['localhost', '00:1A:2B:3C:4D:5E', 'AA:BB:CC:DD:EE:FF', '77:88:99:AA:BB:CC', '11:22:33:44:55:66', '01:23:45:67:89:AB', '12:34:56:78:9A:BC', 'AB:CD:EF:12:34:56', '98:76:54:32:10:FE', '44:55:66:77:88:99']
cities = ['New York', 'Tokyo', 'Paris', 'Sydney', 'Dubai', 'São Paulo', 'London', 'Mumbai', 'Cairo', 'Shanghai']

def generate_random_float(min_value, max_value):
    return random.uniform(min_value, max_value)


while True:
    # Pick a random item from the list
    random_devices = random.choice(devices)
    random_hosts = random.choice(hosts)
    random_cities = random.choice(cities)

    random_float = generate_random_float(0.0, 1000.0)
    # print(f"Random float: {random_float}")
    body = MetricPayload(
        series=[
            MetricSeries(
                metric="test.monitor",
                type=MetricIntakeType.UNSPECIFIED,
                points=[
                    MetricPoint(
                        timestamp=int(datetime.now().timestamp()),
                        value=random_float,
                    ),
                ],
                tags=[f'host:{random_hosts}', f'device:{random_devices}', f'cities:{random_cities}'],
                resources=[
                    MetricResource(
                        name="localhost",
                        type="host",
                    ),
                ],
            ),
        ],
    )

    configuration = Configuration()
    configuration.api_key['apiKeyAuth'] = config['datadog']['apiKeyAuth']
    configuration.api_key['appKeyAuth'] = config['datadog']['appKeyAuth']
    configuration.host = config['datadog']['host']
    with ApiClient(configuration) as api_client:
        api_instance = MetricsApi(api_client)
        response = api_instance.submit_metrics(body=body)
        print(response)
