var fs = require("fs");

var tagPattern = /<\/?([A-Z])>/g;

function isClosingTag(tag) {
    return tag.indexOf('/') > 0;
}

function checkTagMatch(line) {
    if (!line) {
        return;
    }
    var tagStack = [];
    var match;
    var expectedTagName;
    tagPattern.lastIndex = 0; //reset regex search offset.
    
    while (match = tagPattern.exec(line)) {
        var tag = match[1];
        if (isClosingTag(match[0])) {
            expectedTag = tagStack.pop();
            if (tag != expectedTag) {
                console.log("Expected %s found </%s>",
                            expectedTag ? ("</" + expectedTag + ">") : "#",
                            tag);
                return;
            }
        } else {
            tagStack.push(tag);
        }
    }

    expectedTag = tagStack.pop();
    if (expectedTag) {
        console.log("Expected </%s> found #", expectedTag);
    } else {
        console.log("Correctly tagged paragraph");
    }
}

var fileName = process.argv[2];
if (!fileName) {
    console.log("Usage: node tag_checker.js filename");
    process.exit(1);
}

fs.readFile(fileName, {encoding: "UTF-8"}, function(err, data) {
    if (err) {
        console.log("Error reading file: %s", err);
        return;
    }
    var lines = data.split(/\r?\n/);
    lines.forEach(checkTagMatch);
});
