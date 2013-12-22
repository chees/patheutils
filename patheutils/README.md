Run locally:

    mvn appengine:devserver


Setup Google Cloud Storage bucket:

    gsutil setdefacl public-read gs://patheutils.chees.info
    gsutil web set -m index.html -e 404.html gs://patheutils.chees.info
    gsutil cors set gcscors.xml gs://patheutils.chees.info


Deploy:

    mvn appengine:update
