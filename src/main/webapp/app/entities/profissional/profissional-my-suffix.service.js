(function() {
    'use strict';
    angular
        .module('fisioacusApp')
        .factory('Profissional', Profissional);

    Profissional.$inject = ['$resource', 'DateUtils'];

    function Profissional ($resource, DateUtils) {
        var resourceUrl =  'api/profissionals/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dtInicio = DateUtils.convertLocalDateFromServer(data.dtInicio);
                        data.dtTermino = DateUtils.convertLocalDateFromServer(data.dtTermino);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dtInicio = DateUtils.convertLocalDateToServer(copy.dtInicio);
                    copy.dtTermino = DateUtils.convertLocalDateToServer(copy.dtTermino);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dtInicio = DateUtils.convertLocalDateToServer(copy.dtInicio);
                    copy.dtTermino = DateUtils.convertLocalDateToServer(copy.dtTermino);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
