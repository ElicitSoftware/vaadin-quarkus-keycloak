docker exec -it $1 sh -c "cp -rp /h2 /tmp; /opt/keycloak/bin/kc.sh export --log-level info --file /tmp/vaadin-realm-export.json --realm vaadin --users same_file --optimized --db-url 'jdbc:h2:file:/tmp/h2/keycloakdb;NON_KEYWORDS=VALUE' --http-management-port 9001"
# Copy the exported file from the container to the host
docker cp $1:/tmp/elicit-realm-export.json ./vaadin-realm-export.json