"""
Send deflate logs returns "Request accepted for processing (always 202 empty JSON)." response
"""

import configparser
from datadog_api_client import ApiClient, Configuration
from datadog_api_client.v2.api.logs_api import LogsApi
from datadog_api_client.v2.model.content_encoding import ContentEncoding
from datadog_api_client.v2.model.http_log import HTTPLog
from datadog_api_client.v2.model.http_log_item import HTTPLogItem

# Create a ConfigParser object
config = configparser.ConfigParser()

# Read the config.ini file
config.read('config.ini')

body = HTTPLog(
    [
        HTTPLogItem(
            ddsource="solutiongigs",
            ddtags="env:testing,version:5.1",
            hostname="localhost",
            message="2019-11-19T14:37:58,995 INFO [process.name][20081] Hello World",
            service="payment",
        ),
    ]
)

configuration = Configuration()
configuration.api_key['apiKeyAuth'] = config['datadog']['apiKeyAuth']
configuration.api_key['appKeyAuth'] = config['datadog']['appKeyAuth']
configuration.host = config['datadog']['host']
with ApiClient(configuration) as api_client:
    api_instance = LogsApi(api_client)

    print(api_instance)
    response = api_instance.submit_log(content_encoding=ContentEncoding.DEFLATE, body=body)

    print(response)
