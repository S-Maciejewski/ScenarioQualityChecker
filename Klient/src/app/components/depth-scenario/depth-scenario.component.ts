import { Component, OnInit } from '@angular/core';
import { HttpHelper } from '../../utils/HttpHelper.service';
import { MenuComponent } from 'src/app/menu/menu/menu.component';
import { HttpClient } from '@angular/common/http';
import * as _ from 'underscore';

export class Steps {
  steps: any;
}

@Component({
  selector: 'app-depth-scenario',
  templateUrl: './depth-scenario.component.html',
  styleUrls: ['./depth-scenario.component.scss']
})
export class DepthScenarioComponent implements OnInit {

  menu: MenuComponent;
  httpHelper: HttpHelper;
  http: HttpClient;
  depth: any;
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

  isDepthCorrect() {
    if(!_.isEmpty(this.depth))
      if(this.depth.match(/^\d{1,}$/g))
        return true;
      else return false
    else
      return false;
  }

  getStepsWithDepth() {
    this.output = 'An error occured, sorry';
    this.httpHelper.getDepthSteps(this.http, this.menu.getScenarioInput(), this.depth).subscribe((data) => {
      if (data.hasOwnProperty('steps')) {
        this.output = this.parseResponse(<Steps>data);
      }
    });
  }
}
