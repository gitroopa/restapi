Feature: API tests for https://api.openweathermap.org/data/2.5

  @SysTest1
  Scenario Outline: Verify API responses from openweathermap.org for different parameters
    Given user executes "<http>" request api "<endpint>" with params "<paramsList>"
    When response code is <ResponseCODE>
    Then Verifies that "<message>" is present in the response
    Examples:
      | http | endpint                             | paramsList                | ResponseCODE | message                   |
      | GET  | ENDPOINT_GET_OpenWeatherMap         | #q=London                 | 200          | $.name=London             |
      | GET  | ENDPOINT_GET_OpenWeatherMap         | #q=London                 | 200          | $.sys.country=GB          |
      | GET  | ENDPOINT_GET_OpenWeatherMap         | #id=2172797               | 200          | $.name=Cairns             |
      | GET  | ENDPOINT_GET_OpenWeatherMap         | #lat=35#lon=139           | 200          | $.name=Shuzenji           |
      | GET  | ENDPOINT_GET_OpenWeatherMap         | #zip=94040                | 200          | $.sys.country=US          |
      | GET  | ENDPOINT_GET_OpenWeatherMapWithBox  | #bbox=12,32,15,37,10      | 200          | $.list[0].name=Birkirkara |
      | GET  | ENDPOINT_GET_OpenWeatherMapWithFind | #lat=55.5#lon=37.5#cnt=10 | 200          | $.list[9].name=Podolsk    |







