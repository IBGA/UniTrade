import { loadFeature, defineFeature } from 'jest-cucumber';
import { beforeEach } from 'vitest';
import TestRenderer from 'react-test-renderer';
import { BrowseItemPostingPage } from '../../pages/BrowseItemPostingPage';
import { get, post } from '../../utils/client';
import {ItemPostingThumbnail} from "../../components/ItemPosting.jsx";

const feature = loadFeature('../features/ID021_Browse_all_item_postings.feature');

// vitest.mock('react-select', () => ({
//   // Mock implementation for startListeningComposition function
//   startListeningComposition: () => {},
//   // You can add more mock implementations for other functions used by react-select here
// }));

let testRenderer = TestRenderer.create(<BrowseItemPostingPage/>);
let testInstance = testRenderer.root;

defineFeature(feature, (test) => {
  test('Browse all available item postings (Normal Flow)', ({ given, and, when, then }) => {
    given('user is logged in', () => {
      // not implemented yet
    });

    and('there exists at least one item posting', async () => {
      let items = await get('itemPosting');
      if (items.length == 0) {

        let universityBody = {
          name: "Test University",
          city: "Test City",
          description: "Test Description",
          moderationIds: [],
        }

        let universityRes = await post('university', universityBody);

        let personBody = {
          email: "test@mail.com",
          password: "test",
          firstName: "Test",
          lastName: "Test",
          username: "test",
          universityId: universityRes.id,
          lastOnline: "2023-02-27T05:35:25.955Z",
          profilePicture: "string",
          enrolledCourseIds: [],
          online: true,
          enabled: true,
        }

        let personRes = await post('person', personBody);

        let itemBody = {
          title: 'Test Item',
          description: 'Test Description',
          price: 10,
          universityId: universityRes.id,
          posterId: personRes.id,
          datePosted: '2023-02-27T05:35:25.955Z',
          courseIds: [],
        }

        let itemRes = await post('itemPosting', itemBody);
      }
    });

    when('user searches for all available item postings', () => {
      // empty cz UI sets the default to all postings
    });

    then('all available item postings are displayed', async () => {
      let itemPostings = testInstance.findAllByType(ItemPostingThumbnail);
      let onDatabase = await get('itemposting');
      expect(itemPostings.length).toBe(onDatabase.length);
    });
  });


  test('No available item postings (Alternate Flow)', ({ given, and, when, then }) => {
    given('user is logged in', () => {
      // not implemented yet
    });

    and('there are no available item postings', async () => {
      let itemPostingIds = (await get('itemposting')).map(item => item.id);
      itemPostingIds.forEach(id => {
        delete('itemposting', id);
      });
    });

    when('user searches for all available item postings', () => {
      // empty cz UI sets the default to all postings
    });

    then('no item postings are displayed', () => {
      // Check that the count of the items displayed is equal to 0
      let itemPostings = testInstance.findAllByType(ItemPostingThumbnail);
      expect(itemPostings.length).toBe(0);
    });
  });
});