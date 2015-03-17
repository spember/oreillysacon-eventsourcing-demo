/**
 * A small pub/sub file inspired from this gist:
 * https://gist.github.com/Yitsushi/e3b7823f7d4bf34faa4f
 *
 */
var EventSystem = (function() {
    var self = this;

    self.queue = {};

    self.processQueue = function (queue, data) {
        var i = 0,
            max = queue.length;
        for (i; i < max; i++) {
            queue[i](data);
        }
    };

    return {
        publish: function (event, data) {
            var queue = self.queue[event];

            if (typeof queue === 'undefined') {
                return false;
            }

            self.processQueue(queue, data);

            return true;
        },
        subscribe: function(event, callback) {
            if (typeof self.queue[event] === 'undefined') {
                self.queue[event] = [];
            }

            self.queue[event].push(callback);
        }
    };
}());