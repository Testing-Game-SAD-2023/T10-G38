ECHO Installazione iniziale e avvio dei container
ECHO Creazione volume condiviso
docker volume create shared_data
ECHO Creazione container ClassRepo
cd T1-G20-main\T1-G20-main\classUT-repository 
docker build -t t1 . --no-cache
docker-compose up -d 
ECHO Creazione contanier front-end
cd ..\..\..\T5-G5-main\T5-G5-main
xcopy build \T5-G5-main\ /s /Y 
cd T5-G5-main
docker build -t t5front . --no-cache
docker-compose up -d
ECHO Creazione container back-end
cd backend\
docker build -t t5back . --no-cache
docker-compose up -d 
ECHO Creazione container editor
cd ..\..\..\..\T6-G16-main\T6-G16-main\spring\editor 
docker build -t t6 . --no-cache
docker run -p 8190:8080 -d -v shared_data:/FileSystem --name editor t6 
ECHO Creazione container compiler
cd ..\..\..\..\T7-G23-main\T7-G23-main 
docker build -t t7 . --no-cache
docker run -p 5000:8080 -d -v shared_data:/FileSystem --name compiler t7
