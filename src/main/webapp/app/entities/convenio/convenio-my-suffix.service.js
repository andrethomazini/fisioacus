(function() {
    'use strict';
    angular
        .module('fisioacusApp')
        .factory('Convenio', Convenio);

    Convenio.$inject = ['$resource'];

    function Convenio ($resource) {
        var resourceUrl =  'api/convenios/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
