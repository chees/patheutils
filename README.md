Run scraper locally:

	cd patheutils
    mvn appengine:devserver

Run site locally:

    cd static
    python -m SimpleHTTPServer

Setup Google Cloud Storage bucket:

    gsutil setdefacl public-read gs://patheutils.chees.info
    gsutil web set -m index.html -e 404.html gs://patheutils.chees.info
    gsutil cors set gcscors.xml gs://patheutils.chees.info

Deploy scraper:

	cd patheutils
    mvn appengine:update
    open http://patheutils.appspot.com/cron

Deploy site:

    ./deploystatic.sh
    open http://patheutils.chees.info
