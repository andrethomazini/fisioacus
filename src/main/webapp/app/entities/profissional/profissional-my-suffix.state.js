(function() {
    'use strict';

    angular
        .module('fisioacusApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('profissional-my-suffix', {
            parent: 'entity',
            url: '/profissional-my-suffix?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'fisioacusApp.profissional.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/profissional/profissionalsmySuffix.html',
                    controller: 'ProfissionalMySuffixController',
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
                    $translatePartialLoader.addPart('profissional');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('profissional-my-suffix-detail', {
            parent: 'profissional-my-suffix',
            url: '/profissional-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'fisioacusApp.profissional.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/profissional/profissional-my-suffix-detail.html',
                    controller: 'ProfissionalMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('profissional');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Profissional', function($stateParams, Profissional) {
                    return Profissional.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'profissional-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('profissional-my-suffix-detail.edit', {
            parent: 'profissional-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/profissional/profissional-my-suffix-dialog.html',
                    controller: 'ProfissionalMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Profissional', function(Profissional) {
                            return Profissional.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('profissional-my-suffix.new', {
            parent: 'profissional-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/profissional/profissional-my-suffix-dialog.html',
                    controller: 'ProfissionalMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                dtInicio: null,
                                dtTermino: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('profissional-my-suffix', null, { reload: 'profissional-my-suffix' });
                }, function() {
                    $state.go('profissional-my-suffix');
                });
            }]
        })
        .state('profissional-my-suffix.edit', {
            parent: 'profissional-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/profissional/profissional-my-suffix-dialog.html',
                    controller: 'ProfissionalMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Profissional', function(Profissional) {
                            return Profissional.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('profissional-my-suffix', null, { reload: 'profissional-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('profissional-my-suffix.delete', {
            parent: 'profissional-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/profissional/profissional-my-suffix-delete-dialog.html',
                    controller: 'ProfissionalMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Profissional', function(Profissional) {
                            return Profissional.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('profissional-my-suffix', null, { reload: 'profissional-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
