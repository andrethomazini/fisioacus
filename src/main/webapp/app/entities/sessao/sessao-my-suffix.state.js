(function() {
    'use strict';

    angular
        .module('fisioacusApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('sessao-my-suffix', {
            parent: 'entity',
            url: '/sessao-my-suffix?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'fisioacusApp.sessao.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sessao/sessaosmySuffix.html',
                    controller: 'SessaoMySuffixController',
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
                    $translatePartialLoader.addPart('sessao');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('sessao-my-suffix-detail', {
            parent: 'sessao-my-suffix',
            url: '/sessao-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'fisioacusApp.sessao.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sessao/sessao-my-suffix-detail.html',
                    controller: 'SessaoMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('sessao');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Sessao', function($stateParams, Sessao) {
                    return Sessao.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'sessao-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('sessao-my-suffix-detail.edit', {
            parent: 'sessao-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sessao/sessao-my-suffix-dialog.html',
                    controller: 'SessaoMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Sessao', function(Sessao) {
                            return Sessao.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sessao-my-suffix.new', {
            parent: 'sessao-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sessao/sessao-my-suffix-dialog.html',
                    controller: 'SessaoMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                descricao: null,
                                dtInicio: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('sessao-my-suffix', null, { reload: 'sessao-my-suffix' });
                }, function() {
                    $state.go('sessao-my-suffix');
                });
            }]
        })
        .state('sessao-my-suffix.edit', {
            parent: 'sessao-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sessao/sessao-my-suffix-dialog.html',
                    controller: 'SessaoMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Sessao', function(Sessao) {
                            return Sessao.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sessao-my-suffix', null, { reload: 'sessao-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sessao-my-suffix.delete', {
            parent: 'sessao-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sessao/sessao-my-suffix-delete-dialog.html',
                    controller: 'SessaoMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Sessao', function(Sessao) {
                            return Sessao.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sessao-my-suffix', null, { reload: 'sessao-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
