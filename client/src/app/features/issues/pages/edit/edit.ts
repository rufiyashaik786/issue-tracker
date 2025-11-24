import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

import { IssueService } from '../../services/issue.service';
import { Issue } from '../../../../shared/models/issue.model';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-issue-edit',
  standalone: true,
  imports: [CommonModule, RouterModule, ReactiveFormsModule],
  templateUrl: './edit.html',
  styleUrls: ['./edit.css'],
})
export class IssueEditComponent implements OnInit {
  form!: FormGroup;
  issueId!: number;

  loading = true;
  submitting = false;
  errorMessage: string | null = null;

  statuses = ['OPEN', 'IN_PROGRESS', 'CLOSED', 'RESOLVED'];

  priorities = ['LOW', 'MEDIUM', 'HIGH'];

  constructor(
    private route: ActivatedRoute,
    private issueService: IssueService,
    private fb: FormBuilder,
    private router: Router,
    private toastr: ToastrService
  ) {}

  // REQUIRED for template f["field"]
  get f() {
    return this.form.controls;
  }

  ngOnInit(): void {
    this.issueId = Number(this.route.snapshot.paramMap.get('id'));
    this.buildForm();
    this.loadIssue();
  }

  buildForm() {
    this.form = this.fb.group({
      title: ['', Validators.required],
      description: ['', [Validators.required, Validators.minLength(10)]],
      status: ['', Validators.required],
      priority: ['', Validators.required],
    });
  }

  loadIssue() {
    this.loading = true;

    this.issueService.getById(this.issueId).subscribe({
      next: (res: any) => {
        const issue = res.data || res;

        this.form.patchValue({
          title: issue.title,
          description: issue.description,
          status: issue.status,
          priority: issue.priority,
        });

        this.loading = false;
      },
      error: () => {
        this.errorMessage = 'Failed to load issue.';
        this.toastr.error('Error loading issue');
        this.loading = false;
      },
    });
  }

  onSubmit() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.submitting = true;

    const updatedIssue: Partial<Issue> = this.form.value;

    this.issueService.update(this.issueId, updatedIssue).subscribe({
      next: () => {
        this.toastr.success('Issue updated');
        this.submitting = false;
        this.router.navigate(['/issues', this.issueId]);
      },
      error: () => {
        this.toastr.error('Update failed');
        this.submitting = false;
      },
    });
  }

  cancel() {
    this.router.navigate(['/issues', this.issueId]);
  }
}
