/*
Passed - should define a function called shuffle
Passed - shuffle should take 3 parameters. action, inputString and optionalArgs
Passed - shuffle should throw error if the action is not an integer
Passed - shuffle should throw error if action is outside 1 and 4
Passed - when action is 1 move number of chars specified in optionalArgs from end of inputString to beginning
Passed - when action is 2 reverse the string
Passed - when action is 3 return the first character with maximum occurrences
Passed - when action is 4 sort the string as per sorting order described in third parameter
*/

function shuffle(action, inputString, optionalArgs) {
    if (typeof action === "number") {
        const len = inputString.length;
        const arg = optionalArgs;
        switch (action) {
            case 1:
                return inputString.substring(len - arg) + inputString.substring(0, len - arg);
            case 2:
                return inputString.split("").reverse().join("");
            case 3:
                const dict = {};
                let max = 0;
                let maxChar = '';
                for (let i = len - 1; i >= 0; i--) {
                    const char = inputString[i];
                    if (dict[char] === undefined) {
                        dict[char] = 1;
                    } else {
                        dict[char]++;
                    }
                    if (dict[char] >= max) {
                        maxChar = char;
                        max = dict[char];
                    }
                }
                return maxChar;
            case 4:
                let newString = inputString;
                for (let i = 0; i < len; i++) {
                    for (let j = i; j < len; j++) {
                        if (compare(newString[i], newString[j], arg)) {
                        	newString = swapString(newString, i, j);
                        }
                    }
                }
                return newString;
            default:
                throw "action out of range";
        }
    } else {
        throw "invalid action type";
    }
}

function swapString(str, first, last){
    return str.substring(0, first)
           + str[last]
           + str.substring(first + 1, last)
           + str[first]
           + str.substring(last + 1);
}

function compare(i, j, orderString) {
    return orderString.indexOf(i) > orderString.indexOf(j);
}