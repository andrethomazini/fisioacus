(function() {
    'use strict';
    angular
        .module('fisioacusApp')
        .factory('Sessao', Sessao);

    Sessao.$inject = ['$resource', 'DateUtils'];

    function Sessao ($resource, DateUtils) {
        var resourceUrl =  'api/sessaos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dtInicio = DateUtils.convertLocalDateFromServer(data.dtInicio);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dtInicio = DateUtils.convertLocalDateToServer(copy.dtInicio);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dtInicio = DateUtils.convertLocalDateToServer(copy.dtInicio);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
