#!/bin/bash
if [ $TRAVIS_PULL_REQUEST != false ]
then
    echo "Pull request, not deploying"
    bash ./gradlew assembleRelease
elif [ "$TRAVIS_BRANCH" = 'master' ]
then
    echo "Push to master, deploying to beta"
    export PLAY_TRACK='beta'
    bash ./gradlew -PFirebaseServiceAccountFilePath=$TRAVIS_BUILD_DIR/crashreporting_private_key.json publishApkRelease
elif [ "$TRAVIS_BRANCH" = 'develop' ]
then
    echo "Push to develop, deploying to alpha"
    export PLAY_TRACK='alpha'
    bash ./gradlew -PFirebaseServiceAccountFilePath=$TRAVIS_BUILD_DIR/crashreporting_private_key.json publishApkRelease
else
    echo "Push to $TRAVIS_BRANCH, not deploying"
    bash ./gradlew assembleRelease
fi