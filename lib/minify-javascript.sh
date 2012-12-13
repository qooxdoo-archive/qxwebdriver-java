#!/bin/bash

# Compresses all .js files in the ./javascript directory and stores the output
# files in ../src/org/oneandone/qxwebdriver/resources/javascript
# Requires node.js and the uglifyjs package.

find ./javascript -name "*.js" -exec sh -c 'uglifyjs $0 -o ../src/org/oneandone/qxwebdriver/resources/javascript/`basename $0`' {} \;
