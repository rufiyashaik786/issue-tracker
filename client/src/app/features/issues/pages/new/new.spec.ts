import { ComponentFixture, TestBed } from '@angular/core/testing';
import { IssueNewComponent } from './new';

describe('IssueNewComponent', () => {
  let component: IssueNewComponent;
  let fixture: ComponentFixture<IssueNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [IssueNewComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(IssueNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
