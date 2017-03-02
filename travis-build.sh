#!/bin/bash
if [ $TRAVIS_PULL_REQUEST ]
then
    echo "Pull request, not deploying"
    bash ./gradlew assembleRelease
elif [ "$TRAVIS_BRANCH" = 'master' ]
then
    echo "Push to master, deploying to beta"
    EXPORT PLAY_TRACK='beta'
    bash ./gradlew publishApkRelease
elif [ "$TRAVIS_BRANCH" = 'develop' ]
then
    echo "Push to develop, deploying to alpha"
    EXPORT PLAY_TRACK='alpha'
    bash ./gradlew publishApkRelease
else
    echo "Push to $TRAVIS_BRANCH, not deploying"
    bash ./gradlew assembleRelease
fi