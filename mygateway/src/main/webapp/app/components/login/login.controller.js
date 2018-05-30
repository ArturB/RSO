(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .controller('LoginController', LoginController);

    LoginController.$inject = ['$rootScope', '$window', '$state', '$timeout', 'Auth', '$uibModalInstance'];

    function LoginController ($rootScope, $window, $state, $timeout, Auth, $uibModalInstance) {
        var vm = this;

        vm.authenticationError = false;
        vm.timeoutError = false;
        vm.cancel = cancel;
        vm.credentials = {};
        vm.login = login;
        vm.password = null;
        vm.register = register;
        vm.rememberMe = true;
        vm.requestResetPassword = requestResetPassword;
        vm.username = null;
        vm.isLoging = false;
        vm.loginTimeoutTime = 8000;

        $timeout(function (){angular.element('#username').focus();});

        function cancel () {
            vm.credentials = {
                username: null,
                password: null,
                rememberMe: true
            };
            vm.authenticationError = false;
            $uibModalInstance.dismiss('cancel');
        }

        function login (event) {
            event.preventDefault();
            vm.isLoging = true;
            Auth.login({
                username: vm.username,
                password: vm.password,
                rememberMe: vm.rememberMe
            }, angular.noop(), vm.loginTimeoutTime).then(function () {
                vm.isLoging = false;
                vm.authenticationError = false;
                vm.timeoutError = false;
                $uibModalInstance.close();
                if ($state.current.name === 'register' || $state.current.name === 'activate' ||
                    $state.current.name === 'finishReset' || $state.current.name === 'requestReset') {
                    $state.go('home');
                }

                $rootScope.$broadcast('authenticationSuccess');

                // previousState was set in the authExpiredInterceptor before being redirected to login modal.
                // since login is successful, go to stored previousState and clear previousState
                if (Auth.getPreviousState()) {
                    // var previousState = Auth.getPreviousState();
                    Auth.resetPreviousState();
                    // $state.go(previousState.name, previousState.params);
                    $state.go('home');
                }
            }, function (xx) {
                if(xx.status == -1){
                    vm.timeoutError = true;
                    vm.loginTimeoutTime = undefined;
                }
                vm.isLoging = false;
                vm.authenticationError = true;
            }).catch(function (xx) {
                vm.isLoging = false;
                vm.authenticationError = true;
            });
        }

        function register () {
            $uibModalInstance.dismiss('cancel');
            $state.go('register');
        }

        function requestResetPassword () {
            $uibModalInstance.dismiss('cancel');
            $state.go('requestReset');
        }
    }
})();
