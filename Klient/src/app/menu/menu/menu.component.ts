import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit {

  drawerOpened = true;
  input: string;

  constructor() { }

  ngOnInit() {

  }

  getScenarioInput() {
    return JSON.parse(this.input);
  }

  toggleDrawer() {
    this.drawerOpened = !this.drawerOpened;
  }
}
