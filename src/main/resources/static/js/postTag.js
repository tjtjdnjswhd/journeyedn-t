const tagInput = document.getElementById('tag-input');
const tagContainer = document.getElementById('tag-container');
const form = document.getElementById('post-form');
const tags = new Set();
const tagsInput = document.getElementById('tags-hidden');

tagInput.addEventListener('keydown', function (event) {
  if (event.key === 'Enter') {
    event.preventDefault();
    const tagText = this.value.trim();
    if (tagText && !tags.has(tagText)) {
      addTag(tagText);
      this.value = '';
    }
  }
});

function addTag(text) {
  const tag = document.createElement('div');
  tag.className = 'tag';

  const tagText = document.createElement('span');
  tagText.classList.add('tag-text');
  tagText.textContent = '#' + text;

  const tagRemove = document.createElement('button');
  tagRemove.classList.add('tag-remove');
  tagRemove.classList.add('btn');
  tagRemove.classList.add('p-0')

  tagRemove.addEventListener('click', () => removeTag(tagRemove, text))
  tagRemove.role = 'button';

  const closeTagImg = document.createElement('img');
  closeTagImg.src = "/img/CloseButton.svg";
  closeTagImg.height = 20;
  tagRemove.appendChild(closeTagImg);

  tag.appendChild(tagText);
  tag.appendChild(tagRemove);

  tagContainer.appendChild(tag);
  tags.add(text);
}

function removeTag(element, text) {
  element.parentElement.remove();
  tags.delete(text);
}

form.addEventListener('submit', function (e) {
  e.preventDefault();
  tagsInput.value = '[' + Array.from(tags).join(',') + ']';
  this.submit();
});