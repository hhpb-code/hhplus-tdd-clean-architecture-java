# API 명세

## 특강 신청

`POST /v1/lectures/enrollments`

요청

```json
{
  "userId": 1,
  "lectureId": 1,
  "lectureScheduleId": 1
}
```

응답

```json
{
  "lectureEnrollment": {
    "id": 1,
    "userId": 1,
    "lectureId": 1,
    "lectureScheduleId": 1,
    "createdAt": "2024-10-03T00:00:00",
    "updatedAt": null
  }
}
```

## 특강 선택 가능 목록 조회

`GET /v1/lectures/schedules/available`

요청

```json
{
  "date": "2024-10-03"
}
```

응답

```json
{
  "lectureSchedules": [
    {
      "id": 1,
      "lectureId": 1,
      "date": "2024-10-03",
      "capacity": 30,
      "enrolledCount": 0,
      "startTime": "2024-10-03T00:00:00",
      "endTime": "2024-10-03T00:00:00",
      "createdAt": "2024-10-03T00:00:00",
      "updatedAt": null
    },
    {
      "id": 2,
      "lectureId": 2,
      "date": "2024-10-03",
      "capacity": 30,
      "enrolledCount": 0,
      "startTime": "2024-10-03T00:00:00",
      "endTime": "2024-10-03T00:00:00",
      "createdAt": "2024-10-03T00:00:00",
      "updatedAt": null
    }
  ]
}
```

## 특강 신청 완료 목록 조회

`GET /v1/lectures/enrolled`

요청

```json
{
  "userId": 1
}
```

응답

```json
{
  "lectureEnrollments": [
    {
      "id": 1,
      "title": "특강1",
      "description": "특강1 설명",
      "lecturer": {
        "id": 1,
        "name": "홍길동",
        "createdAt": "2024-10-03T00:00:00",
        "updatedAt": null
      },
      "createdAt": "2024-10-03T00:00:00",
      "updatedAt": null
    },
    {
      "id": 2,
      "title": "특강2",
      "description": "특강2 설명",
      "lecturer": {
        "id": 2,
        "name": "김철수",
        "createdAt": "2024-10-03T00:00:00",
        "updatedAt": null
      },
      "createdAt": "2024-10-03T00:00:00",
      "updatedAt": null
    }
  ]
}
```
