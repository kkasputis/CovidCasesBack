# CovidCasesBack

Pull this repository to your computer.

Build docker image: docker build -t covid-cases-back .

Build UI from here: https://github.com/kkasputis/covid-cases-front

Build docker image: docker build -t covid-cases-front .

Run the docker-compose file included with the front-end: docker-compose up --build -d 

Make sure your VM ports 8080 and 4200 are forwardet to localhost.

Open your browser and go to http://localhost:4200
