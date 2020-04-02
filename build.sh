# this script is called when the judge is building your compiler.
# no argument will be passed in.

set -e
echo "Surprise motherfxxker"
cd "$(dirname "$0")"
mkdir -p bin
find ./src/ -name *.java | javac -d bin -cp "./lib/antlr-4.8-complete.jar" @/dev/stdin