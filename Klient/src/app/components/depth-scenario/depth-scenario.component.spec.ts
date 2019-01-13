import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DepthScenarioComponent } from './depth-scenario.component';

describe('DepthScenarioComponent', () => {
  let component: DepthScenarioComponent;
  let fixture: ComponentFixture<DepthScenarioComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DepthScenarioComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DepthScenarioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
