import { Routes } from '@angular/router';

// Issue Pages
import { IssueListComponent } from './features/issues/pages/list/list';
import { IssueNewComponent } from './features/issues/pages/new/new';
import { IssueEditComponent } from './features/issues/pages/edit/edit';
import { IssueDetailComponent } from './features/issues/pages/detail/detail';

// Optional Dashboard (if you use it)
import { DashboardComponent } from './features/dashboard/dashboard';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'issues',
    pathMatch: 'full',
  },

  // // Dashboard (ONLY if you use it)
  {
    path: 'dashboard',
    component: DashboardComponent,
  },

  // LIST Issues
  {
    path: 'issues',
    component: IssueListComponent,
  },

  // CREATE Issue
  {
    path: 'issues/new',
    component: IssueNewComponent,
  },

  // EDIT Issue
  {
    path: 'issues/:id/edit',
    component: IssueEditComponent,
  },

  // VIEW Issue Details
  {
    path: 'issues/:id',
    component: IssueDetailComponent,
  },

  // Catch all (optional)
  {
    path: '**',
    redirectTo: 'issues',
  },
];
