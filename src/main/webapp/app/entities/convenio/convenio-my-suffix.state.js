(function() {
    'use strict';

    angular
        .module('fisioacusApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('convenio-my-suffix', {
            parent: 'entity',
            url: '/convenio-my-suffix?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'fisioacusApp.convenio.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/convenio/conveniosmySuffix.html',
                    controller: 'ConvenioMySuffixController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('convenio');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('convenio-my-suffix-detail', {
            parent: 'convenio-my-suffix',
            url: '/convenio-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'fisioacusApp.convenio.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/convenio/convenio-my-suffix-detail.html',
                    controller: 'ConvenioMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('convenio');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Convenio', function($stateParams, Convenio) {
                    return Convenio.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'convenio-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('convenio-my-suffix-detail.edit', {
            parent: 'convenio-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/convenio/convenio-my-suffix-dialog.html',
                    controller: 'ConvenioMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Convenio', function(Convenio) {
                            return Convenio.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('convenio-my-suffix.new', {
            parent: 'convenio-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/convenio/convenio-my-suffix-dialog.html',
                    controller: 'ConvenioMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                descricao: null,
                                contato: null,
                                telefone: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('convenio-my-suffix', null, { reload: 'convenio-my-suffix' });
                }, function() {
                    $state.go('convenio-my-suffix');
                });
            }]
        })
        .state('convenio-my-suffix.edit', {
            parent: 'convenio-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/convenio/convenio-my-suffix-dialog.html',
                    controller: 'ConvenioMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Convenio', function(Convenio) {
                            return Convenio.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('convenio-my-suffix', null, { reload: 'convenio-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('convenio-my-suffix.delete', {
            parent: 'convenio-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/convenio/convenio-my-suffix-delete-dialog.html',
                    controller: 'ConvenioMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Convenio', function(Convenio) {
                            return Convenio.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('convenio-my-suffix', null, { reload: 'convenio-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
