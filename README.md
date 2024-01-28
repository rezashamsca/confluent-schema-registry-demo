This is a demo of using confluent schema server to verify storage of data. 

Example of registering a schema with the schema server:
```
curl --request POST \
  --url http://localhost:8080/api/v1/schemas/register \
  --header 'Content-Type: application/json' \
  --header 'User-Agent: insomnia/8.6.0' \
  --data '{
	"subject": "person.schema.json",
	"jsonSchema": {
      "$id": "https://com.rtecsoft.alpha/person.schema.json",
      "$schema": "https://json-schema.org/draft-07/schema",
      "title": "Person",
      "type": "object",
      "properties": {
            "firstName": {
                  "type": "string"
            },
            "lastName": {
                  "type": "string"
            },
            "age": {
                  "type": "integer",
                  "minimum": 0
            }
      }
}
}'
```
Example of storing data in mongodb with schema validation:
```
curl --request POST \
  --url http://localhost:8080/api/v1/data/ \
  --header 'Content-Type: application/json' \
  --header 'User-Agent: insomnia/8.6.0' \
  --data '{
	"schemaSubject": "person.schema.json",
	"action": "create",
	"payload": {
		"firstName": "John",
        "lastName": "Doe",
        "age": 21
	}
}'
```
