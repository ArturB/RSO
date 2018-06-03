(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .factory('ElectoralPeriod', ElectoralPeriod);

    ElectoralPeriod.$inject = ['$resource', '$q'];

    function ElectoralPeriod($resource, $q) {
        var _periods = undefined;
        var _debugPeriod = null;

        var service = $resource('msapp/api/electoral-periods', {}, {
            'get': { method: 'GET', params: {}, isArray: true,
                interceptor: {
                    response: function(response) {
                        // expose response
                        var arr = response.data;
                        for(var i = 0; i < arr.length; i++){
                            var element = arr[i];
                            element.startDate = new Date(element.startDate);
                            element.endDate = new Date(element.endDate);
                        }
                        return response;
                    }
                }
            }
        });

        var selectCurrentPeriod = function(){
            var currentDate = new Date();
            for (var i = 0; i < _periods.length; i++) {
                if (_periods[i].startDate < currentDate && _periods[i].endDate >= currentDate) {
                    return (_periods[i]);
                }
            }
        };

        service.getCurrentPeriod = function() {
            var deferred = $q.defer();
            // _debugPeriod = {
            //     name:'MidRoundPeriod'
            // };
            if(_debugPeriod){
                deferred.resolve(angular.copy(_debugPeriod));
            }else if(_periods){
                deferred.resolve(angular.copy(selectCurrentPeriod()));
            }else{
                service.get().$promise.then(function (result) {
                    _periods = result.data;
                    deferred.resolve(angular.copy(selectCurrentPeriod()));
                });
            }
            return deferred.promise;
        };

        service.getCurrentRound = function(){
            return service.getCurrentPeriod().then(function (result){
                if(result.name === 'MidRoundPeriod'){
                    return 1;
                }else if (result.name === 'PostElectionPeriod'){
                    return 2;
                }else{
                    return 0;
                }
            });
        }

        service.translatePeriod = function(name){
            if(name === "PreElectionPeriod"){
                return "Okres przedwyborczy";
            }else
            if(name === "FirstRoundPeriod"){
                return "Okres pierwszej tury";
            }else
            if(name === "MidRoundPeriod"){
                return "Okres między turami";
            }else
            if(name === "SecondRoundPeriod"){
                return "Okres drugiej tury";
            }else
            if(name === "PostElectionPeriod"){
                return "Okres po wyborach";
            }else{
                return "?";
            }
        };

        service.setDebugPeriod = function(periodName){
            var startDate = new Date();
            startDate.setDate(startDate.getDate() - 2);
            var endDate = new Date();
            endDate.setDate(endDate.getDate() + 2);

            _debugPeriod = {
                name : periodName,
                startDate : startDate,
                endDate : endDate
            };
        };

        return service;
    }
})();
