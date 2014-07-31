#!/bin/sh
export RAW_FILE=/tmp/export.csv
export NORMALIZED_RAW_FILE=/tmp/user-item-pref.csv
export TARGET_DIR=/usr/share/nginx/html/api/recommendation/
export R_SCRIPT=/usr/share/pizza/recommendation/recommendation.R

echo "Recommendation-Generation started:  $(date)"
rm "$RAW_FILE" "$NORMALIZED_RAW_FILE"
mongoexport -d pizza -c recommendation.preference --csv --fields userId,articleId,preference --out "$RAW_FILE"
cat /tmp/export.csv | sed 's/ObjectID(//' | sed 's/),"/,/' | sed 's/",/,/' > "$NORMALIZED_RAW_FILE"
mkdir -p "$TARGET_DIR"
Rscript "$R_SCRIPT" "$NORMALIZED_RAW_FILE" "$TARGET_DIR/all"
echo "Recommendation-Generation finished:  $(date)"
