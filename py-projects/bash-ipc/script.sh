#!/bin/bash

log() {
    echo "$1"
}


for i in {1..10}; do
    log "in bash: $i"
done

log "finished."
