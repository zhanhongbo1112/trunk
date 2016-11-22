define(["dojo/_base/Deferred"], function (Deferred) {
    return {
        dynamicRequire: function (dependencies) {
            var d = new Deferred();
            var callback = function () {
                d.resolve.apply(this, Array.prototype.slice.call(arguments));
            };
            require(dependencies, callback);

            return d;
        }
    };
});