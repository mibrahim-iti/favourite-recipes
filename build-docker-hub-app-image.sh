#! /bin/bash

for argument in "$@"
  do
    key=$(echo $argument | cut --fields 1 --delimiter='=')
    value=$(echo $argument | cut --fields 2 --delimiter='=')

    case "$key" in
      "version")        version="$value" ;;
      *)
    esac
  done

validate_image_version() {
  if [ -z "$version" ]
  then
        echo "\$version is NULL, please add version variable when you run the script [example -> build-app-image-digital-ocean.sh version=01]"
        exit 0
  fi
}

do_build_image() {
  container_name=favourite-recipes-digital-ocean-container

  docker build . -t mibrahimid/favourite-recipes:$version
  docker run --name $container_name -p 8080:8080 -it --rm --detach mibrahimid/favourite-recipes:$version

  container_id=$(docker ps -aqf "name=$container_name")
  echo "container id = $container_id"
  docker commit $container_id mibrahimid/favourite-recipes:$version

  echo "Login into docker hub with username mibrahimid ..."
  docker login -u mibrahimid

  echo "Pushing created image to docker hub..."
  docker push mibrahimid/favourite-recipes:$version
}

validate_image_version
do_build_image
