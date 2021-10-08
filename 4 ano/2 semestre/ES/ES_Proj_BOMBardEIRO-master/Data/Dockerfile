FROM python:3
WORKDIR /usr/src/app
COPY . .

COPY requirements.txt /tmp/

RUN pip3 install --upgrade -r /tmp/requirements.txt

CMD ["producer.py"]
ENTRYPOINT ["python3"]
