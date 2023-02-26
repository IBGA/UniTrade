import { loadFeatures, autoBindSteps, loadFeature } from 'jest-cucumber';

// Just import all the step definitions here
import { exampleSteps } from './step_definitions/example.steps.js';
import { addUniversitySteps } from './step_definitions/addUniversity.steps.jsx';
import { commonSteps } from './step_definitions/common.steps.js';

const features = loadFeatures(
  'src/../../features/ID024_Add_new_university.feature'
);

// Add the imported step definitions into the second argument array
autoBindSteps(features, [exampleSteps, commonSteps, addUniversitySteps]);
