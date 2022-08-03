## Setup

To setup the Lane Merge web app do the following steps.

1. Build the vanetza docker following the markdown file instructions on the [vanetza](https://code.nap.av.it.pt/mobility-networks/vanetza) submodule.
2. Use the [docker-compose.yml](https://github.com/tiagoadonis/Lane_Merge/blob/master/docker-compose.yml) file in the vanetza docker and build the docker with: `docker build -t vanetza:test .`
3. Install all the requirements needed doing the following command: `pip install -r requirements.txt`

## Run Lane Merge

To run the project do the following command on the root directory: `./lane_merge.sh`
