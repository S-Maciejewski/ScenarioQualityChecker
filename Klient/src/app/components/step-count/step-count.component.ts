import { Component, OnInit } from '@angular/core';
import { HttpHelper } from '../../utils/HttpHelper.service';
import { MenuComponent } from 'src/app/menu/menu/menu.component';
import { HttpClient } from '@angular/common/http';

export class WrongSteps {
  wrongSteps: any[];
}

@Component({
  selector: 'app-step-count',
  templateUrl: './step-count.component.html',
  styleUrls: ['./step-count.component.scss']
})
export class StepCountComponent implements OnInit {
  menu: MenuComponent;
  httpHelper: HttpHelper;
  http: HttpClient;
  countOutput: any;
  wrongCountOutput: any;
  keywordCountOutput: any;

  constructor(menu: MenuComponent,
    helper: HttpHelper,
    httpClient: HttpClient) {
    this.httpHelper = helper;
    this.menu = menu;
    this.http = httpClient;
  }

  ngOnInit() {
  }


  parseCountData(data: any) {
    return data.stepsNumber;
  }

  parseWrongStepsData(data: WrongSteps) {
    return data.wrongSteps.length;
  }

  parseKeywordCountData(data: any) {
    return data.keywordStepsNumber;
  }

  getStepsCount() {
    this.countOutput = 'An error occured, sorry';
    this.httpHelper.getStepsCount(this.http, this.menu.getScenarioInput()).subscribe((data) => {
      if (data.hasOwnProperty('stepsNumber')) {
        console.log('Res: ', data);
        this.countOutput = this.parseCountData(data);
      }
    });
  }

  getWrongStepsCount() {
    this.wrongCountOutput = 'An error occured, sorry';
    this.httpHelper.getWrongSteps(this.http, this.menu.getScenarioInput()).subscribe((data) => {
      if (data.hasOwnProperty('wrongSteps')) {
        console.log('Res: ', data);
        this.wrongCountOutput = this.parseWrongStepsData(<WrongSteps>data);
      }
    });
  }

  getKeywordCount() {
    this.keywordCountOutput = 'An error occured, sorry';
    this.httpHelper.getKeywordCount(this.http, this.menu.getScenarioInput()).subscribe((data) => {
      if (data.hasOwnProperty('keywordStepsNumber')) {
        console.log('Res: ', data);
        this.keywordCountOutput = this.parseKeywordCountData(data);
      }
    });
  }

}
