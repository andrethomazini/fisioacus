(function() {
    'use strict';
    angular
        .module('fisioacusApp')
        .factory('Pessoa', Pessoa);

    Pessoa.$inject = ['$resource', 'DateUtils'];

    function Pessoa ($resource, DateUtils) {
        var resourceUrl =  'api/pessoas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dtNascimento = DateUtils.convertLocalDateFromServer(data.dtNascimento);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dtNascimento = DateUtils.convertLocalDateToServer(copy.dtNascimento);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dtNascimento = DateUtils.convertLocalDateToServer(copy.dtNascimento);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
