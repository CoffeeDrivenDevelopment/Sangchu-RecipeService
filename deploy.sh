#!/bin/sh

##########
# ENV
##########

export RECIPE_SERVICE_IMAGE_NAME="cdd/recipe-service"
export RECIPE_SERVICE_TAG_NAME="0.0.1"

##########
# Build Api Gateway Image
##########

echo "\n🗑 Start Delete Docker Files"

if docker image inspect $RECIPE_SERVICE_IMAGE_NAME:$RECIPE_SERVICE_TAG_NAME &> /dev/null; then
    docker image rm -f $RECIPE_SERVICE_IMAGE_NAME:$RECIPE_SERVICE_TAG_NAME
fi

echo "\n🔨 Start Build Docker Image"

docker build \
-t $RECIPE_SERVICE_IMAGE_NAME:$RECIPE_SERVICE_TAG_NAME .

##########
# Api Gateway Container Start
##########

if [ "$(docker ps -aq -f name=$RECIPE_SERVICE_NAME)" ]; then
    echo "🚫 Stop Docker Container : "
    docker rm -f $RECIPE_SERVICE_NAME
else
    echo "🚫 There is no running container named $RECIPE_SERVICE_NAME"
fi

echo "🚀 Docker $RECIPE_SERVICE_NAME Container Start! : "
docker run -d \
--name $RECIPE_SERVICE_NAME \
-p $RECIPE_SERVICE_PORT:$RECIPE_SERVICE_PORT \
-e PROFILE=$RECIPE_SERVICE_PROFILE \
$RECIPE_SERVICE_IMAGE_NAME:$RECIPE_SERVICE_TAG_NAME