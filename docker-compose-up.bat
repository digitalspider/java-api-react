cd ./api/
CALL mvnw package
cd ..
REM CALL npm run --prefix ./ui eslint-fix
CALL npm run --prefix ./ui build
docker-compose up
