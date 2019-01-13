import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";

const apiAdress = 'http://localhost:8090/';

const api = {
  showScenario: 'showScenario',
  countSteps: 'countSteps',
  countKeywords: 'countKeyWordSteps',
  wrongSteps: 'wrongSteps'
}

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
  }),
}

@Injectable({
  providedIn: 'root',
})
export class HttpHelper {

  getSteps(http: HttpClient, scenario: string) {
    console.log('scenario @ HttpHelper', scenario);
    return (http.post(apiAdress + api.showScenario, JSON.stringify(scenario), httpOptions));
  }

  getDepthSteps(http: HttpClient, scenario: string, depth: number) {
    console.log('scenario @ HttpHelper', scenario);
    return (http.post(apiAdress + api.showScenario + '/' + depth, JSON.stringify(scenario), httpOptions));
  }

  getStepsCount(http: HttpClient, scenario: string) {
    console.log('scenario @ HttpHelper', scenario);
    return (http.post(apiAdress + api.countSteps, JSON.stringify(scenario), httpOptions));
  }

  getKeywordCount(http: HttpClient, scenario: string) {
    console.log('scenario @ HttpHelper', scenario);
    return (http.post(apiAdress + api.countKeywords, JSON.stringify(scenario), httpOptions));
  }

  getWrongSteps(http: HttpClient, scenario: string) {
    console.log('scenario @ HttpHelper', scenario);
    return (http.post(apiAdress + api.wrongSteps, JSON.stringify(scenario), httpOptions));
  }
}