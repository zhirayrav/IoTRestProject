# IoTRestProject
# Project Title

REST API for IoT devices, their events with payload


## Authors

- [@zhirayrav](https://github.com/zhirayrav)
## API Reference

#### Registre new device

```http
  POST http://localhost:8080/device/registration
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `serialNumber` | `string` | **Required**.serial number of device (unique) |
| `name` | `string` | **Required**.name of device(unique) |
| `type` | `string` | **Required**.one of [bluetooth,wi-fi) |

Response example: 
```
jwt_token: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJTZXJpYWwgbnVtYmVyIjoiMTJ5eXkiLCJzdWIiOiJTZXJpYWwgbnVtYmVyIiwiaXNzIjoiQXZhbmVzeWFuIiwiaWF0IjoxNjc4OTk5MjgwfQ.yHWELF8aFbgVFHp-y6h2pDTJ8_Pvv8qYx9i9vGVLFPU
```

#### Get all devices [with pagination]

```http
  GET http://localhost:8080/device
```
| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `page` | `int` | for pagination, page count |
| `devicePerPage` | `int` | for pagination, page count per page |


#### Get device by serialNumber

```http
  GET http://localhost:8080/device/{serialNumber}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `serialNumber`      | `string` | serial number of device |

#### Get device with filter by type
```http
  GET http://localhost:8080/device/filterByType
```
| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `type` | `string` | **Required**. type of device. [bluetooth,wi-fi] |

#### Get device with filter by type
```http
  GET http://localhost:8080/device/filterByDate
```
| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `from` | `string` | ISO Date Time Format yyyy-MM-dd'T'HH:mm:ss.SSSXXX — for example, "2000-10-31T01:30:00" |
| `to` | `string` | ISO Date Time Format yyyy-MM-dd'T'HH:mm:ss.SSSXXX — for example, "2000-10-31T01:30:00" |


#### Get all active devices (devices that sent an event in the last 30 minutes)
```http
  GET http://localhost:8080/active-device
```

Response for device (example): 
```
[
    {
        "id": 1,
        "serialNumber": "12aaa",
        "name": "testDevice",
        "type": "BLUETOOTH",
        "secretKey": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJTZXJpYWwgbnVtYmVyIjoiMTJhYWEiLCJzdWIiOiJTZXJpYWwgbnVtYmVyIiwiaXNzIjoiQXZhbmVzeWFuIiwiaWF0IjoxNjc4NzM2MTUyfQ.FkxmrN1tPXWr7U-74lpcFh9ewZkFPZJs17zY8dqc4kY",
        "addedAt": "2023-03-13T22:35:52.227089",
        "events": [
            {
                "id": 3,
                "type": "MOTION",
                "happenedAt": "2023-03-13T23:41:37.52398",
                "payload": {
                    "speed": 12,
                    "distance": 50
                }
            }
        ]
]
```
#### Register new event

```http
  POST http://localhost:8080/event
```
| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `secretKey` | `string` | **Required** secret key received during device registration |
| `type` | `string` | **Required** type of event. one of [weather,motion]|
| `payload` | `Payload` | **Required** nested object. [{double temperature,double humidity} or {int speed,int distance}]|

Response: 
```
 Ok
 ```

 #### Get events by serialNumber of device [with pagination]

```http
  GET http://localhost:8080/event/{serialNumber}
```
| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `serialNumber` | `string` |**Required**  events count per page|
| `page` | `int` | page count for pagination|
| `eventsPerPage` | `int` | events count per page|


#### Get events by serialNumber of device with filter by date

```http
  GET http://localhost:8080/event/{serialNumber}/filterByDate
```
| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `from` | `string` | ISO Date Time Format yyyy-MM-dd'T'HH:mm:ss.SSSXXX — for example, "2000-10-31T01:30:00"|
| `to` | `string` |ISO Date Time Format yyyy-MM-dd'T'HH:mm:ss.SSSXXX — for example, "2000-10-31T01:30:00"|

Response (example):
```
[
    {
        "id": 25,
        "type": "WEATHER",
        "happenedAt": "2023-03-16T15:51:21.829304",
        "payload": {
            "temperature": 40.1,
            "humidity": 5.1
        }
    },
    {
        "id": 26,
        "type": "MOTION",
        "happenedAt": "2023-03-16T17:05:55.821534",
        "payload": {
            "speed": 40,
            "distance": 10
        }
    }
]
```

#### Get events by duration group by types of device

```http
  GET http://localhost:8080/event/count/
```
| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `from` | `string` | ISO Date Time Format yyyy-MM-dd'T'HH:mm:ss.SSSXXX — for example, "2000-10-31T01:30:00"|
| `to` | `string` |ISO Date Time Format yyyy-MM-dd'T'HH:mm:ss.SSSXXX — for example, "2000-10-31T01:30:00"|

Response (example):
```
[
    {
        "type": "BLUETOOTH",
        "eventCount": 6
    },
    {
        "type": "WI_FI",
        "eventCount": 11
    }
]
```
