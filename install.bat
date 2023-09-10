	::creazione del volume condiviso
	docker volume create shared_data
	
	//reset dei file necessari al container di mantenimento delle classi
	cd T1-G20-main\T1-G20-main\classUT-repository
	mkdir tmp
	robocopy /mir tmp .\ClassiUT\
	robocopy /mir tmp .\database\ 
	rmdir tmp
	//build e compose up dell'immagine
	docker build -t t1 . --no-cache
	docker-compose up -d
	
	//reset dei file necessari al game engine
	cd ..\..\..\T5-G5-main\T5-G5-main
	xcopy build \T5-G5-main\ /s /Y 
	cd T5-G5-main
	//build e compose up dell'immagine del front-end
	docker build -t t5front . --no-cache
	docker-compose up -d
	cd backend\
	//build e compose up dell'immagine del back-end
	docker build -t t5back . --no-cache
	docker-compose up -d
	
	//creazione di immagine e container editor 
	cd ..\..\..\..\T6-G16-main\T6-G16-main\spring\editor 
	docker build -t t6 . --no-cache
	//esecuzione del container su porta :8190 e montaggio del volume condiviso
	docker run -p 8190:8080 -d -v shared_data:/FileSystem --name editor t6
	
	//creazione di immagine e container compilatore
	cd ..\..\..\..\T7-G23-main\T7-G23-main 
	docker build -t t7 . --no-cache
	//esecuzione del container su porta :5000 e montaggio del volume condiviso
	docker run -p 5000:8080 -d -v shared_data:/FileSystem --name compiler t7