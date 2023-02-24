import { loadFeatures, autoBindSteps } from 'jest-cucumber';

// Just import all the step definitions here
import { exampleSteps } from './step_definitions/example.steps.js';
import { commonSteps } from './step_definitions/common.steps.js';
import { userAccountSteps } from './step_definitions/id015RegisterUserAccount.steps.js';
import { createNewItemSteps } from './step_definitions/id017CreateNewItem.steps.js';
import { browseByUniversityAndCourseSteps } from './step_definitions/id019BrowseByUniversityAndCourse.steps.js';
import { loginSteps } from './step_definitions/id020Login.steps.js';
import { browseAllItemsSteps } from './step_definitions/id021BrowseAllItems.steps.js';
import { addNewUniversitySteps } from './step_definitions/id024AddNewUniversity.steps.js';
import { addNewCourseSteps } from './step_definitions/id025AddNewCourse.steps.js';

const features = loadFeatures('src/../../features/*.feature');

// Add the imported step definitions into the second argument array
autoBindSteps(features, [
  exampleSteps,
  commonSteps,
  userAccountSteps,
  createNewItemSteps,
  browseByUniversityAndCourseSteps,
  loginSteps,
  browseAllItemsSteps,
  addNewUniversitySteps,
  addNewCourseSteps,
]);
