(function (exports) {
    exports.app = new Vue({
        el: '#content',
        data: {
            characterMount: null,
            dataReady: true,
            interval: null,
            autoRefresh: false,
            lastRefresh: null
        },
        ready: function () {
            this.fetchData();
        },
        methods: {
            fetchData: function () {
                var vm = this;
                this.$http.get('mount/?character=rageraven&realm=magtheridon').then(function (response) {
                    console.log("got response", response);
                    vm.characterMount = response.body;
                    vm.lastRefresh = formatTimestamp(new Date());
                    vm.dataReady = true;
                }, function (response) {
                    console.log(response.status);
                });
            }
        }
    });
})(window);

function formatTimestamp(date) {
    return pad(date.getDate()) + '.' + pad((date.getMonth() + 1)) + '.' + date.getFullYear() + ' '
           + pad(date.getHours()) + ':' + pad(date.getMinutes()) + ':' + pad(date.getSeconds());
}

function pad(value) {
    if (value < 10) {
        return '0' + value;
    } else {
        return value;
    }
}