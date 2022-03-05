aws dynamodb create-table \
    --table-name lenguaje \
    --attribute-definitions \
        AttributeName=name,AttributeType=S \
    --key-schema AttributeName=name,KeyType=HASH \
    --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1 \
    --profile default \
    --endpoint-url http://localhost:4566