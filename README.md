# ODK SMS Input

Allows SMS input into an [Open Data Kit (ODK)](http://opendatakit.org/) 2.0
database.

## Overview

ODK SMS Input lets you use SMS messages to input data into an ODK 2.0 database.
This allows you to use SMS to communicate with the ODK 2.0 tools and the cloud.
Once ODK SMS Input is configured on a device, messages received can be
processed and appear alongside other data in the database.

When used with the [ODK SMS Bridge](https://github.com/srsudar/odk-sms-bridge),
you can use Survey on a phone without internet connectivity, communicate that
data to a phone running ODK SMS Input, and it will all appear in the database.

NB: This is a work in progress and is not yet ready for primetime.

## Usage

For now, you will have to write a processor and a parser in Java, build the
app locally, and install it. Eventually we hope to make this possible via
JavaScript, which will not require building from source, but for now you still
have to write the parsing and processing in Java.

## Building

Because the current ODK tools are maintained as
[hg](http://mercurial.selenic.com/) projects and rely on Eclipse builds rather
than the new Gradle/IntelliJ hotness, this project for now also follows an
Eclipse structure and build.

Clone the [androidcommon](https://code.google.com/p/opendatakit/source/checkout?repo=androidcommon)
repository from the ODK source page. This is an Android Library Project that
handles interactions with the ODK 2.0 database and tool suite. Update to the
`development` branch by running `hg update development`. This is the active
branch in the ODK development process. Import the `androidcommon` project. (You
don't need the `androidcommon.test` project, but you can import it as well if
you'd like.)

Then clone this repository and import the `odk-sms-input` and
`odk-sms-input-test` projects into your workspace. You should now have all
three projects visible in your workspace. It is difficult to analyze and
predict eclipse build errors, but you should be able to get all the projects to
build, perhaps after some fiddling. All the tests in the `odk-sms-input-test`
project should pass.


## Not Currently Supported by Core ODK Team

This app is currently not supported by the core [Open Data Kit (ODK)](
http://opendatakit.org/) team.

However, this **is** a supported project. Feel free to open issues and use it,
but for now you can't necessarily count on support from the entire ODK core
team.
