define(["dojo/_base/Deferred"], function (Deferred) {
    return {
        dynamicRequire: function (dependencies, localRequire) {
            var d = new Deferred();
            localRequire = localRequire || require;
            var callback = function () {
                d.resolve.apply(this, Array.prototype.slice.call(arguments));
            }
            localRequire(dependencies, callback);

            return d;
        }
    };
});