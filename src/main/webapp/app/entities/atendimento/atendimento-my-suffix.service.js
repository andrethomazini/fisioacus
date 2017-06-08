(function() {
    'use strict';
    angular
        .module('fisioacusApp')
        .factory('Atendimento', Atendimento);

    Atendimento.$inject = ['$resource', 'DateUtils'];

    function Atendimento ($resource, DateUtils) {
        var resourceUrl =  'api/atendimentos/:id';

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
