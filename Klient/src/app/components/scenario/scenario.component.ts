import { Component, OnInit } from '@angular/core';
import { HttpHelper } from '../../utils/HttpHelper.service';
import { MenuComponent } from 'src/app/menu/menu/menu.component';
import { HttpClient } from '@angular/common/http';

export class Steps {
  steps: any;
}

@Component({
  selector: 'app-scenario',
  templateUrl: './scenario.component.html',
  styleUrls: ['./scenario.component.scss']
})
export class ScenarioComponent implements OnInit {

  menu: MenuComponent;
  httpHelper: HttpHelper;
  http: HttpClient;
  output: any;

  constructor(menu: MenuComponent,
    helper: HttpHelper,
    httpClient: HttpClient) {
    this.httpHelper = helper;
    this.menu = menu;
    this.http = httpClient;
  }

  ngOnInit() {

  }

  parseResponse(data: Steps) {
    let steps = data.steps;
    let output: String = '';
    for (let i in steps) {
      for (let j in steps[i]) {
        if (steps[i].charAt(j) == '.' && <any>j % 2 != 0 && <any>j > 1) output += '  ';
      }
      output += steps[i] + '\n';
    }
    return output;
  }

  getSteps() {
    this.output = 'An error occured, sorry';
    this.httpHelper.getSteps(this.http, this.menu.getScenarioInput()).subscribe((data) => {
      if (data.hasOwnProperty('steps')) {
        this.output = this.parseResponse(<Steps>data);
      }
    });
  }
}
