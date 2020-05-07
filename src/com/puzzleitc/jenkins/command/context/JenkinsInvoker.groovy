package com.puzzleitc.jenkins.command.context

import groovy.json.JsonSlurper

/** this is deliberately not a real class, otherwise the build-in methods of Jenkins do not work */

Object callSh(Map args) {
    sh(args)
}

Object callWithEnv(List<String> env, Closure<Object> closure) {
    withEnv(env) {
        closure.call()
    }
}

def callAnsiColor(String colorMapName, Closure<Void> closure) {
    ansiColor(colorMapName) {
        closure.call()
    }
}

String callTool(String toolName) {
    tool(toolName)
}

String callEnv() {
    return env
}

Object callOpenshift() {
    return openshift
}

def callEcho(String message) {
    echo message
}

String lookupValueFromVault(String path, String key) {
    withVault(vaultSecrets: [[path: path, engineVersion: 2, secretValues: [[envVar: 'secretValue', vaultKey: key]]]]) {
        return secretValue
    }
}

String lookupTokenFromCredentials(String credentialsId) {
    withCredentials([string(credentialsId: credentialsId, variable: 'secretValue')]) {
        def jsonObj = new JsonSlurper().parseText("$secretValue")
        return new String(jsonObj.token.decodeBase64())
    }
}