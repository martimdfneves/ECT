# As in https://mherman.org/blog/dockerizing-a-react-app/
# pull official base image
FROM node:14.16.1-alpine
# set working directory
WORKDIR /react
# add `/react/node_modules/.bin` to $PATH
ENV PATH /react/node_modules/.bin:$PATH
# install react dependencies
COPY package.json ./
RUN npm install reactstrap
RUN npm install bootstrap
RUN npm install jquery
RUN npm install axios
RUN npm install google-maps-react
RUN npm install react-google-maps
RUN npm install react-map-gl
RUN npm install swr
RUN npm install react-scripts
RUN npm install

# add react
COPY . ./
# start react
CMD ["npm", "start"]